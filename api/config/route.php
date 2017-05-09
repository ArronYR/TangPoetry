<?php
use think\Route;

// 首页模块
Route::rule('/','index/Index/index');

// 接口模块
Route::rule('/api','api/Index/index');
Route::get('/api/poetry/:id','api/Poetry/one', [], ['id'=>'\d+']);
Route::get('/api/poetry/poetries','api/Poetry/poetries');
Route::get('/api/poetry/count','api/Poetry/count');
Route::get('/api/poetry/search','api/Poetry/search');
Route::get('/api/poetry/rand','api/Poetry/rand');

Route::get('/api/poet/:id','api/Poet/one', [], ['id'=>'\d+']);
Route::get('/api/poet/poets','api/Poet/poets');
Route::get('/api/poet/all','api/Poet/all');
Route::get('/api/poet/count','api/Poet/count');
Route::get('/api/poet/search','api/Poet/search');
