<?php 
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("../conectiondb.php");
        $plate=$_POST['licencePlate'];

        $query = "SELECT * FROM `vehicle` WHERE `licenceplate` = '$plate'";
        $result = $mysql->query($query);

        if($result->num_rows>0){
            while($row = $result->fetch_assoc()){
                $array = $row;
            }          
    
            $query_delete_vehicle = "DELETE FROM `vehicle` WHERE `licenceplate` = '$plate'";
            $result_delete_vehicle = $mysql->query($query_delete_vehicle);
            if($result_delete_vehicle===TRUE){
                http_response_code(200);
                echo json_encode($array); 
            }else{
                http_response_code(500);
                echo json_encode(array("message" => "Error could not be deleted"));
            }         
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Vehicle not found"));
        }
        
        $mysql->close();
    }
