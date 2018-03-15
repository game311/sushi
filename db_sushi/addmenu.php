<?php

$host='127.0.0.1';
$username='root';
$pwd='';
$db='db_sushi';

$con=mysqli_connect($host,$username,$pwd,$db) or die('Unable to connect');
mysqli_set_charset($con, "utf8");

if(!$con)
{
	echo "Connect Error".mysqli_connect_error();
}
else
{
	echo "Connect Success";
}

$nums = $_POST["num"];
$idfoods = $_POST["idfood"];
$idnametables = $_POST["id_nameTable"];
$idnameorders = $_POST["id_nameorder"];


$sql_query = "INSERT INTO orders (num_menu, menus_id, nameorders_id, tables_id) VALUES ($nums, $idfoods, $idnameorders, $idnametables)";

if($con->query($sql_query) === TRUE)
{
	echo "Data Insert Success";
}
else
{
	echo "error";
}

$con->close();
?>