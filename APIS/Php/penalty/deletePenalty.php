<?php 
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("../conectiondb.php");
        $id=$_POST['id'];

        $query = "SELECT * FROM `penalty` WHERE `id` = '$id'";
        $result = $mysql->query($query);

        if($result->num_rows>0){
            while($row = $result->fetch_assoc()){
                $array = $row;
            }
            $query_delete_penalty = "DELETE FROM `penalty` WHERE `id` = '$id'";
            $result_delete_penalty = $mysql->query($query_delete_penalty);
    
            if($result_delete_penalty===TRUE){
                http_response_code(200);
                echo json_encode($array);                
                
            }else{
                http_response_code(500);
                echo json_encode(array("message" => "Error could not be deleted"));
            }
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Penalty not found"));
        }
        
        $mysql->close();
    }
