<?php 
include_once "config.php";
include_once "conexion.php";
$dbConn =  connect($db);

/*
  listar todos los posts o solo uno
 */
if ($_SERVER['REQUEST_METHOD'] == 'GET')
{
      //Mostrar lista de post
      $sql = $dbConn->prepare("SELECT * FROM historial");
      $sql->execute();
      $sql->setFetchMode(PDO::FETCH_ASSOC);
      header("HTTP/1.1 200 OK");
      echo json_encode( $sql->fetchAll()  );
      exit();
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $input = $_POST;
    $sql = "INSERT INTO historial (tiempo,descripcion) VALUES (:tiempo, :descripcion)";
    $statement = $dbConn->prepare($sql);
    bindAllValues($statement, $input);
    $statement->execute();
    $postId = $dbConn->lastInsertId();
    if($postId)
    {
      $input['id'] = $postId;
      header("HTTP/1.1 200 OK");
      echo json_encode($input);
      exit();
	 }
}


?>