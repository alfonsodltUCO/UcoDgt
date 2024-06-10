<?php 
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("../conectiondb.php");
        $id = $_POST['id'];
        $dni = isset($_POST['dni']) ? $_POST['dni'] : "";

        if ($dni === "") {
            $query = "SELECT * FROM `penalty` WHERE `id` = '$id'";
        } else {
            $query = "SELECT * FROM `penalty` WHERE `id` = '$id' AND `dni_client` = '$dni'";
        }

        $result = $mysql->query($query);

        if($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
                $array = $row;
            }
            http_response_code(200);
            echo json_encode($array);
        } else {
            http_response_code(404);
            echo json_encode(array("message" => "Penalty not found"));
        }
        $mysql->close();
    }
?>
