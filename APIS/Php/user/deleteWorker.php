<?php 
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("../conectiondb.php");
        $dni=$_POST['dni'];

        $query = "SELECT * FROM `worker` WHERE `dni_worker` = '$dni'";
        $result = $mysql->query($query);

        if($result->num_rows>0){
            while($row = $result->fetch_assoc()){
                $array = $row;
            }
            $query_delete_worker = "DELETE FROM `worker` WHERE `dni_worker` = '$dni'";
            $result_delete_worker = $mysql->query($query_delete_worker);
    
            if($result_delete_worker===TRUE){
                
                http_response_code(200);
                echo json_encode($array);                
                
            }else{
                http_response_code(500);
                echo json_encode(array("message" => "Error could not be deleted"));
            }
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Worker not found"));
        }
        
        $mysql->close();
    }
