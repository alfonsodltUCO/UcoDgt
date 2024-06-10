<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");

    $licencePlate = $_POST['licencePlate'];
    $validItvFrom = $_POST['validItvFrom'];
    $validItvTo = $_POST['validItvTo'];

    try {

        $check_query = "SELECT * FROM `vehicle` WHERE `licenceplate` = '$licencePlate'";
        $check_result = $mysql->query($check_query);

        if ($check_result->num_rows == 0) {
            http_response_code(404);
            echo "Error: Vehicle not found.";
            exit;
        }


        $update_query = "UPDATE `vehicle` SET `validItvFrom` = '$validItvFrom', `validItvTo` = '$validItvTo' WHERE `licenceplate` = '$licencePlate'";
        $update_result = $mysql->query($update_query);

        if($update_result === true) {
            $response = array('licencePlate' => $licencePlate, 'validItvFrom' => $validItvFrom, 'validItvTo' => $validItvTo);
            http_response_code(200);
            echo json_encode($response);
        } else {
            http_response_code(500);
            echo "Error: " . $mysql->error;
        }

    } catch (mysqli_sql_exception $e) {
        http_response_code(500);
        echo "Error: " . $e->getMessage();
    }
    
    $mysql->close();
}
