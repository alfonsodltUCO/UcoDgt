<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");

    if(isset($_POST['state']) && isset($_POST['dni'])){
        $state = $_POST['state'];
        $client_dni = $_POST['dni'];

        $query = "SELECT * FROM `penalty` WHERE `state` = '$state' AND `dni_client` = '$client_dni'";
        
        $result = $mysql->query($query);

        if($result->num_rows > 0){
            $array = array();
            while($row = $result->fetch_assoc()){
                $array[] = $row;
            }
            http_response_code(200);
            $JsonToSend = array("penalties" => $array);
            echo json_encode($JsonToSend);
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Penalties not found for the given state and client DNI"));
        }
    }else{
        http_response_code(400);
        echo json_encode(array("message" => "Missing parameters"));
    }
    $mysql->close();
}
