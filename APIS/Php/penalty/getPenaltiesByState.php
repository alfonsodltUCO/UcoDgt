<?php
if($_SERVER['REQUEST_METHOD']=='GET'){
    require_once("../conectiondb.php");

    if(isset($_GET['state'])){
        $state = $_GET['state'];

        $query = "SELECT * FROM `penalty` WHERE `state` = '$state'";
        
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
            echo json_encode(array("message" => "Penalties not found for state given"));
        }
    }else{
        http_response_code(400);
        echo json_encode(array("message" => "Missing parameters"));
    }
    $mysql->close();
}

