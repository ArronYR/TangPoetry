<?php

namespace app\common\model;

use think\Model;

class Poetry extends Model
{
    // 设置当前模型对应的完整数据表名称
    protected $table = 'poetries';

    public function poet()
    {
        return $this->belongsTo('Poet', 'poet_id');
    }
}
