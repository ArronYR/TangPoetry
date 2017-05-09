<?php

namespace app\common\model;

use think\Model;

class Poet extends Model
{
    protected $table = 'poets';

    public function poetries()
    {
        return $this->hasMany('Poetry', 'poet_id');
    }
}
