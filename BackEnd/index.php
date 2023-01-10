<?php
    require_once 'user.php';
    require_once 'firebase.php';
    $username = "";
    $password = "";
    $email = "";
    $msg = "";
    if(isset($_POST['username'])){
        $username = $_POST['username'];
    }
    if(isset($_POST['password'])){
        $password = $_POST['password'];
    }
    if(isset($_POST['email'])){
        $email = $_POST['email'];
    }
    if(isset($_POST['msg'])){
        $msg = $_POST['msg'];
    }

    $userObject = new User();
       // Registration
    if(!empty($username) && !empty($password) && !empty($email)){    
        //$hashed_password = md5($password);
        $json_registration = $userObject->createNewRegisterUser($username, $password, $email);
        echo json_encode($json_registration);
    }
    // Login
    else if(!empty($username) && !empty($password)){
        $json_array = $userObject->loginUsers($username, $password);
        echo json_encode($json_array);
    }
    if(!empty($msg)){
        $json = array(); 
        $json['success'] = 2;
        $json['message'] = "Successfully";  
        sendnote($msg);
        echo json_encode($json);
    }   
?>