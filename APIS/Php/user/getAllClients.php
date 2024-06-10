<?php 
if($_SERVER['REQUEST_METHOD']=='GET'){
    require_once("../conectiondb.php");

    $query = "SELECT user.*, client.* FROM `user`
              JOIN `client` ON user.dni_user = client.dni_client";
    $result = $mysql->query($query);

    if($result->num_rows > 0){
        $array = array();
        while($row = $result->fetch_assoc()){
            $array[] = $row;
        }
        http_response_code(200);
        $JsonToSend = array("clients" => $array);
        echo json_encode($JsonToSend);
    }else{
        http_response_code(404);
        echo json_encode(array("message" => "Clients not found"));
    }
    $mysql->close();
}