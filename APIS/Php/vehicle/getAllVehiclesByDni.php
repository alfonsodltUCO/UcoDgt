<?php 
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("../conectiondb.php");
        $dni = $_POST['dni'];
        
        $query = "SELECT * FROM `vehicle` WHERE `dni_client` = '$dni'";
        $result = $mysql->query($query);

        if($result->num_rows>0){
            while($row = $result->fetch_assoc()){
                $array[] = $row;
            }
            http_response_code(200);
            $JsonToSend=array("vehicles" => $array);
            echo json_encode($JsonToSend);
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Vehicles not found"));
        }
        $mysql->close();
    }