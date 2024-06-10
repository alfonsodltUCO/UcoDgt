<?php 
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("../conectiondb.php");
        $id=$_POST['id'];
        $quantity=$_POST['quantity'];

$query_update = "UPDATE `penalty` SET `state` = 'paid', `quantity` = '$quantity' WHERE `id` = '$id'";
        $result_update = $mysql->query($query_update);

        if($result_update){
            $query_select = "SELECT * FROM `penalty` WHERE `id` = '$id'";
            $result_select = $mysql->query($query_select);

            if($result_select->num_rows > 0){
                $penalty = $result_select->fetch_assoc();
                http_response_code(200);
                echo json_encode($penalty);
            } else {
                http_response_code(404);
                echo json_encode(array("message" => "Penalty not found after update"));
            }
        } else {
            http_response_code(500);
            echo json_encode(array("message" => "Failed to update penalty state"));
        }
        
        $mysql->close();
    }
?>
