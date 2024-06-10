<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");

    $dni = $_POST['dni'];
    $id = $_POST['id'];
    $quantity = $_POST['quantity'];
    $points = $_POST['points'];
    $dniW = $_POST['dniW'];

    try {
  
    $select_penalty_query = "SELECT * FROM `penalty` WHERE `id`='$id' AND `dni_worker`='$dniW'";
    $select_penalty_result = $mysql->query($select_penalty_query);

   
    if($select_penalty_result->num_rows > 0) {
       
        $update_penalty_query = "UPDATE `penalty` SET `state`='cancelled', `quantity`=$quantity, `points`=0 WHERE `id`='$id' AND `dni_worker`='$dniW'";
        $update_penalty_result = $mysql->query($update_penalty_query);

        if($update_penalty_result === true) {
           
            $fetch_client_query = "SELECT `licencepoints` FROM `client` WHERE `dni_client`='$dni'";
            $fetch_client_result = $mysql->query($fetch_client_query);

          
            if($fetch_client_result->num_rows > 0) {
                $client_data = $fetch_client_result->fetch_assoc();
                $current_points = (int)$client_data['licencepoints'];

               
                $new_points = min(15, $points + $current_points);

               
                $update_client_query = "UPDATE `client` SET `licencepoints`='$new_points', `dateLastUpdate`=CURDATE() WHERE `dni_client`='$dni'";
                $update_client_result = $mysql->query($update_client_query);

             
                if($update_client_result === true) {
		    //'dni' => $dni, 'licencePoints' => $new_points
                    http_response_code(200);
                    echo json_encode($select_penalty_result->fetch_assoc());
                } else {
                    http_response_code(500);
                    echo json_encode(array("message" => "An error occurred while updating client points."));
                }
            } else {
                http_response_code(404);
                echo json_encode(array("message" => "Client not found."));
            }
        } else {
            http_response_code(400);
            echo json_encode(array("message" => "This penalty doesn't correspond to the worker."));
        }
    } else {
        http_response_code(405);
        echo json_encode(array("message" => "Penalty not found for the worker."));
    }
} catch (mysqli_sql_exception $e) {
    http_response_code(500);
    echo "Error: " . $e->getMessage();
}

    
    $mysql->close();
}
