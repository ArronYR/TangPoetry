<?php
namespace app\api\controller;

use app\api\controller\Base;

use app\common\model\Poetry as Pm;
use app\common\model\Poet as Pt;
use think\Db;

/**
 * 所有接口均支持的参数：
 *     1. 语言 lang=zh-cn|en-us
 *     2. 是否包含诗人信息 poet=0|1 默认 1
 */
class Poetry extends Base
{
    private $hasPoet = true;
    private $hasRows = true;
    private $order = 'asc';

    public function _initialize()
    {
        parent::_initialize();
        if (null == input('poet') || intval(input('poet')) !== 0) {
            $this->hasPoet = true;
        }else{
            $this->hasPoet = false;
        }
        if (null == input('rows') || intval(input('rows')) !== 0) {
            $this->hasRows = true;
        }else{
            $this->hasRows = false;
        }
        if (trim(input('order')) != 'desc') {
            $this->order = 'asc';
        }else{
            $this->order = 'desc';
        }
    }
    /**
     * 根据id获取诗词
     * @param  [type] $id [description]
     * @return [type]     [description]
     */
    public function one($id=0)
    {
        $poetry = Pm::get(intval($id));
        $result["poetry"] = $this->processPoetry($poetry);
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poem')]);
    }

    /**
     * 分页获取诗词
     * @return [type] [description]
     */
    public function poetries()
    {
        // 查询数据分页
        $poetries = Pm::order('id', $this->order)->paginate(config('poem_per_page'));
        $result["poetries"] = $this->processPoetries($poetries);
        $result["total"] = $this->getCount();
        $result["per_page"] = config('poem_per_page');
        $result["current_page"] = input('page');
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poems')]);
    }

    /**
     * 获取诗词总数
     * @return [type] [description]
     */
    public function count()
    {
        $result["total"] = $this->getCount();
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poems count')]);
    }

    /**
     * 搜索
     *     1. 支持指定字段 key、word
     *     2. 支持分页 page
     * @return [type] [description]
     */
    public function search()
    {
        $keyword = trim(input('word'));
        $key = trim(input('key'));

        $fields = Db::getTableInfo('poetries', 'fields');
        unset($fields['created_at']);
        unset($fields['updated_at']);
        if ($key != '' && !in_array($key, $fields)) {
            $result = null;
            return json(['result'=>$result,'code'=>config('response_code.get_error'),'message'=>lang('field not exist')]);
        }else{
            $key = 'content';
        }
        // if (!$keyword || $keyword == '') {
        //     $result = null;
        //     return json(['result'=>$result,'code'=>config('response_code.get_error'),'message'=>lang('param must be has')]);
        // }
        $poetries = Pm::where($key,'like','%'.$keyword.'%')->order('id', $this->order)->paginate(config('poem_per_page'));
        $result["poetries"] = $this->processPoetries($poetries);
        $result["total"] = Pm::where($key,'like','%'.$keyword.'%')->count();
        $result["per_page"] = config('poem_per_page');
        $result["current_page"] = intval(input('page'));
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poems')]);
    }

    public function rand()
    {
        $query = Db::query("select * from poetries order by rand() limit 1");
        if (!$query) {
            $result = null;
            return json(['result'=>$result,'code'=>config('response_code.get_error'),'message'=>lang('for failure')]);
        }
        $result['poetry'] = $query[0];
        unset($result['poetry']['created_at']);
        unset($result['poetry']['updated_at']);
        if ($this->hasPoet) {
            $result['poetry']['poet'] = Pt::find($query[0]['poet_id'])->hidden(['created_at','updated_at']);
        }
        if ($this->hasRows) {
            $result['poetry']['rows'] = $this->processContent($result['poetry']['content']);
        }
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poem')]);
    }

    /**
     * 查询诗词总数
     * @return [type] [description]
     */
    private function getCount()
    {
        return Pm::count();
    }

    /**
     * 获取诗词所属的诗人信息
     * @param  [type] $poetry [description]
     * @return [type]         [description]
     */
    private function getPoet($poetry)
    {
        if ($poetry) {
            $poet = $poetry->poet;
        }else{
            $poet = null;
        }
        return $poet;
    }

    /**
     * 处理单条诗词数据
     * @param  [type] $poetry [description]
     * @return [type]         [description]
     */
    private function processPoetry($poetry)
    {
        $pt = $poetry->hidden(['created_at','updated_at']);
        if ($this->hasPoet) {
            $pt["poet"] = $this->getPoet($poetry)->hidden(['created_at','updated_at']);
        }
        if ($this->hasRows) {
            $pt["rows"] = $this->processContent($poetry->content);
        }
        return $pt;
    }

    /**
     * 处理诗词列表数据
     * @param  [type] $poetries [description]
     * @return [type]           [description]
     */
    private function processPoetries($poetries)
    {
        $ps = array();
        foreach ($poetries as $k => $poetry) {
            $ps[$k] = $this->processPoetry($poetry);
        }
        return $ps;
    }

    /**
     * 将诗词内容分行
     * @param  string $content [description]
     * @return [type]          [description]
     */
    public function processContent($content='')
    {
        $rows = array();
        $n = 0;
        $contentArr = mb_split("。", $content);
        for ($i=0; $i < count($contentArr); $i++) {
            $str = ($contentArr[$i]."。");
            if (strpos($str, "？") != false) {
                $strArr = mb_split('？', $str);
                for ($j=0; $j < count($strArr) -1; $j++) { 
                    $row["idx"] = $n;
                    $row["content"] = $strArr[$j]."？";
                    array_push($rows, $row);
                    $n++;
                }
                $row["idx"] = $n;
                $row["content"] = $strArr[count($strArr) -1];
                array_push($rows, $row);
            }else{
                $row["idx"] = $n;
                $row["content"] = $str;
                array_push($rows, $row);
                $n++;
            }
        }
        array_pop($rows);
        return $rows;
    }
}
