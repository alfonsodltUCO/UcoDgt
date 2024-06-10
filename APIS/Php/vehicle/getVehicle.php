<?php 
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("../conectiondb.php");
        $vehicle=$_POST['licencePlate'];

        
        $query = "SELECT * FROM `vehicle` WHERE `licenceplate` = '$vehicle'";
        $result = $mysql->query($query);

        if($result->num_rows>0){
            while($row = $result->fetch_assoc()){
                $array = $row;
            }
            http_response_code(200);
            echo json_encode($array);
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Vehicle not found"));
        }
        $mysql->close();
    }
