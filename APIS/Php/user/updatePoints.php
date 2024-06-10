<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");

    $dni = $_POST['dni'];
    $licencePoints = $_POST['licencePoints'];
    $dateLastUpdate=$_POST['dateLastUpdate'];

    try {
        $check_query = "SELECT * FROM `client` WHERE `dni_client`='$dni'";
        $check_result = $mysql->query($check_query);

        if($check_result->num_rows > 0) {
            $update_query = "UPDATE `client` SET `licencepoints`='$licencePoints', `dateLastUpdate`='$dateLastUpdate' WHERE `dni_client`='$dni'";
            $update_result = $mysql->query($update_query);

            if($update_result===true) {
                $response = array('dni'=>$dni, 'licencePoints'=>$licencePoints);
                http_response_code(200);
                echo json_encode($response);
            } else {
                http_response_code(500);
                echo "An error has occurred.";
            }
        } else {
            http_response_code(404);
            echo "Error: Client not found.";
        }
    } catch (mysqli_sql_exception $e) {
        http_response_code(500);
        echo "Error: " . $e->getMessage();
    }
    
    $mysql->close();
}

