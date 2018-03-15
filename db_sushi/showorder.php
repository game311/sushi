<?php

$host='127.0.0.1';
$username='root';
$pwd='';
$db='db_sushi';

$con=mysqli_connect($host,$username,$pwd,$db) or die('Unable to connect');
mysqli_set_charset($con, "utf8");


if(mysqli_connect_error($con))
{
	echo "Failded to connect";
}


$query=mysqli_query($con,"SELECT * FROM orders WHERE status='wait'");



if($query)
{
	while($row=mysqli_fetch_array($query))
	{
		$flag[] = $row;
	}

	print(json_encode($flag));
}

mysqli_close($con);
?>