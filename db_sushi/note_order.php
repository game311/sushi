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

$idorders = $_POST["id_order"];
$notes = $_POST["note"];

$sql_query = "UPDATE orders SET note='$notes' WHERE id='$idorders'";


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