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

$totalprices = $_POST["total_price"];
$idnameorders = $_POST["id_nameorder"];


$sql_query = "INSERT INTO bills (nameorders_id, total_price) VALUES ($idnameorders, $totalprices)";

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