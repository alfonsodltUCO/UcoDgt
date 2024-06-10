<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    require_once("../conectiondb.php");

    $date = $_POST['date'];
    $dniC = $_POST['dniC'];
    $dniW = $_POST['dniW'];
    $state = $_POST['state'];
    $reason = $_POST['reason'];
    $description = $_POST['description'];
    $place = $_POST['place'];
    $informed = $_POST['informed'];
    $locality = $_POST['locality'];
    $licenceplate = $_POST['licenceplate'];
    $quantity = $_POST['quantity'];
    $points = $_POST['points'];

    try {
        
        $queryV = "SELECT * FROM `vehicle` WHERE `licenceplate` = '$licenceplate' AND `dni_client` = '$dniC'";
        $resultV = $mysql->query($queryV);

        if($resultV->num_rows>0){
            $licenceQuery = "SELECT `licencepoints` FROM `client` WHERE `dni_client` = '$dniC'";
            $licenceResult = $mysql->query($licenceQuery);
        
            if ($licenceResult->num_rows > 0) {
                $row = $licenceResult->fetch_assoc();
                $licencepoints = $row['licencepoints'];
        
                $remainingPoints = max(0, intval($licencepoints) - intval($points));

                $query = "INSERT INTO `penalty`(`date`, `dni_client`, `dni_worker`, `state`, `reason`, `description`, `place`, `informedAtTheMoment`, `locality`, `licenceplate`, `quantity`, `points`) VALUES ('$date','$dniC','$dniW','$state','$reason','$description','$place','$informed','$locality','$licenceplate','$quantity','$points')";
                $result = $mysql->query($query);
                if ($result === true) {
                    $updateQuery = "UPDATE `client` SET `licencepoints`='$remainingPoints', `dateLastUpdate`=CURDATE() WHERE `dni_client`='$dniC'";
                    $updateResult = $mysql->query($updateQuery);
                    if ($updateResult !== true) {
                        http_response_code(500);
                        echo json_encode(array("message" => "Error updating client licence points"));
                    }else{
                        $response = array('date' => $date, 'dniC' => $dniC, 'dniW' => $dniW);
                        http_response_code(200);
                        echo json_encode($response);       
                    }
                }else{
                    http_response_code(422);
                    echo json_encode(array("message" => "client doesnt exist"));
                }
              
            }
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Not found this vehicle for the user"));
        } 
    } catch (mysqli_sql_exception $e) {
        if ($e->getCode() == 1452) { 
            http_response_code(400); 
            echo json_encode(array("message" => "Violation of foreign key constraint"));
        } else {
            http_response_code(500);
            echo "Error: " . $e->getMessage();
        }
    }

    $mysql->close();
}
