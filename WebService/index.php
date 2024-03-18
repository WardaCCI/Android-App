<?php 

function error404() {
  header("HTTP/1.0 404 Not Found");
  exit;
}

function get_food($code) {
  require('data.php');
  if ($code == 'random') return $data[rand(0, count($data)-1)];
  if (!preg_match('/^[0-9]{13}$/', $code)) return null;
  foreach ($data as $food) {
    if ($food['code']==$code) return $food;
  }
  return null;
}

function search_foods($query) {
  require('data.php');
  if (strlen($query) <= 2) return [];
  $foods = [];
  foreach ($data as $food) {
    if (stripos($food['name'].$food['brands'], $query)!==false) 
      array_push($foods, $food);
    if (count($foods) >= 20) break;
  }
  return $foods;
}

if (!isset($_SERVER['PATH_INFO'])) error404();
$elements = explode('/', $_SERVER['PATH_INFO']);
if (count($elements)!=3 && $elements[0]!='') error404();

if ($elements[1] == 'food') {
  $code = $elements[2];
  $food = get_food($code);
  if ($food==null) error404();
  echo json_encode($food);
  return;
}

if ($elements[1] == 'search') {
  $query = $elements[2];
  $foods = search_foods($query);
  echo json_encode($foods);
  return;
}

error404();