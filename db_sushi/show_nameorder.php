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

$nameorders = $_POST["nameOrder"];

//$nameorders = 807909;

// $query=mysqli_query($con,"SELECT * FROM nameorders WHERE name_order = '$nameorders'");
$sql_query = "INSERT INTO nameorders (name_order) VALUES ('$nameorders')";
$con->query($sql_query)

// if($query)
// {
// 	while($row=mysqli_fetch_array($query))
// 	{
// 		$flag[] = $row;
// 	}

// 	print(json_encode($flag));
// }



$con->close();
?>