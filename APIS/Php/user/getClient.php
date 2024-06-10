<?php 
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");
    $dni=$_POST['dni'];

    $query = "SELECT client.*, user.* FROM `client`
              JOIN `user` ON client.dni_client = user.dni_user
              WHERE client.dni_client = '$dni'";
    $result = $mysql->query($query);

    if($result->num_rows > 0){
        $array = array();
        while($row = $result->fetch_assoc()){
            $array[] = $row;
        }
        http_response_code(200);
        echo json_encode($array[0]);
    }else{
        http_response_code(404);
        echo json_encode(array("message" => "Client not found"));
    }
    $mysql->close();
}
