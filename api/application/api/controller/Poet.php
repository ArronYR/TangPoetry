<?php
namespace app\api\controller;

use app\api\controller\Base;

use app\common\model\Poet as Pt;
use think\Db;

/**
 * 所有接口均支持的参数：
 *     1. 语言 lang=zh-cn|en-us
 *     2. 是否包含诗人的所有诗词信息 poem=0|1 默认 1
 */
class Poet extends Base
{
    private $hasPoem = true;
    private $hasRows = true;
    private $order = 'asc';

    public function _initialize()
    {
        parent::_initialize();
        if (null == input('poem') || intval(input('poem')) !== 0) {
            $this->hasPoem = true;
        }else{
            $this->hasPoem = false;
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
     * 根据id获取诗人
     * @param  [type] $id [description]
     * @return [type]     [description]
     */
    public function one($id=0)
    {
        $poet = Pt::get(intval($id));
        $result["poet"] = $this->processPoet($poet);
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poet')]);
    }

    public function poets()
    {
        // 查询数据分页
        $poets = Pt::order('id', $this->order)->paginate(config('poet_per_page'));
        $result["poets"] = $this->processPoets($poets);
        $result["total"] = $this->getCount();
        $result["per_page"] = config('poet_per_page');
        $result["current_page"] = input('page');
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poets')]);
    }

    public function all()
    {
        // $poets = Pt::order('id', $this->order)->select();
        // $this->hasPoem = false;
        // $result["poets"] = $this->processPoets($poets);
        // $result["total"] = $this->getCount();
        $json_string = file_get_contents(APP_PATH."poets.json");
        $data = json_decode($json_string, true);
        $result["poets"] = $data["data"];
        $result["total"] = $data["total"];
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poets')]);
    }

    public function count()
    {
        $result["total"] = $this->getCount();
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poets count')]);
    }

    public function search()
    {
        $keyword = trim(input('word'));
        $key = trim(input('key'));

        $fields = Db::getTableInfo('poets', 'fields');
        unset($fields['created_at']);
        unset($fields['updated_at']);
        if ($key != '' && !in_array($key, $fields)) {
            $result = null;
            return json(['result'=>$result,'code'=>config('response_code.get_error'),'message'=>lang('field not exist')]);
        }else{
            $key = 'name';
        }
        $poets = Pt::where($key,'like','%'.$keyword.'%')->order('id', $this->order)->paginate(config('poet_per_page'));
        $result["poets"] = $this->processPoets($poets);
        $result["total"] = Pt::where($key,'like','%'.$keyword.'%')->count();
        $result["per_page"] = config('poet_per_page');
        $result["current_page"] = intval(input('page'));
        return json(['result'=>$result,'code'=>config('response_code.get_success'),'message'=>lang('success for poets')]);
    }

    /**
     * 查询诗人总数
     * @return [type] [description]
     */
    private function getCount()
    {
        return Pt::count();
    }

    /**
     * 处理单条诗人数据
     * @param  [type] $poetry [description]
     * @return [type]         [description]
     */
    private function processPoet($poet)
    {
        $pt = $poet->hidden(['created_at','updated_at']);
        $pt["count"] = count($pt->poetries);
        if ($this->hasPoem) {
            foreach ($pt->poetries as $k => $poetry) {
                $ps[$k] = $poetry->hidden(['created_at','updated_at']);
                if ($this->hasRows) {
                    $ps[$k]["rows"] = $this->processContent($poetry->content);
                }
            }
            $pt["poetries"] = $ps;
        }else{
            unset($pt["poetries"]);
        }
        return $pt;
    }

    /**
     * 处理诗人列表数据
     * @param  [type] $poets [description]
     * @return [type]           [description]
     */
    private function processPoets($poets)
    {
        $ps = array();
        foreach ($poets as $k => $poet) {
            $ps[$k] = $this->processPoet($poet);
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
