<?php
namespace app\api\controller;

use think\Controller;
use think\Request;

/**
* 基础控制器
*/
class Base extends Controller
{
    public function _initialize()
    {
        header('Access-Control-Allow-Origin: *');
        header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
        header('Access-Control-Allow-Methods: GET, POST, PUT');
    }
}