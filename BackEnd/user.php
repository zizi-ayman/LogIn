<?php
    include_once 'db-connect.php';
    class User{
        private $db;
        public function __construct(){
            $this->db = new DbConnect();
        }
        public function isLoginExist($username, $password){   
            $query = "select * from users where username = '$username' AND password = $password;";
            $result = mysqli_query($this->db->getDb(), $query);
            if(mysqli_num_rows($result) > 0){
                mysqli_close($this->db->getDb());
                return true;
            }
            mysqli_close($this->db->getDb());
            return false;
        }
        public function isEmailUsernameExist($username, $email){
            $query = "select * from users where username = '$username' AND email = '$email'";
            $result = mysqli_query($this->db->getDb(), $query);
            if(mysqli_num_rows($result) > 0){
                mysqli_close($this->db->getDb());
                return true;
            }
            return false;
        }
        public function isValidEmail($email){
            return filter_var($email, FILTER_VALIDATE_EMAIL) !== false;
        }
        
        public function createNewRegisterUser($username, $password, $email){     
            $isExisting = $this->isEmailUsernameExist($username, $email);
            if($isExisting){
                $json['success'] = 0;
                $json['message'] = "Error in registering. Probably the username or email already exists";
            }else{   
                $isValid = $this->isValidEmail($email);
                    if($isValid){
                    $query = "insert into users (username, password, email) values ('$username', '$password', '$email')";
                    $inserted = mysqli_query($this->db->getDb(), $query);
                    if($inserted == 1){
                        $json['success'] = 1;
                        $json['message'] = "Successfully registered the user";
                    }else{
                        $json['success'] = 0;
                        $json['message'] = "Error in registering. Probably the username or email already exists";   
                    }
                    mysqli_close($this->db->getDb());
                    }else{
                        $json['success'] = 0;
                        $json['message'] = "Error in registering. Email Address is not valid";
                    }
                }
            return $json;
        }
        public function getbatteryinfo(){     
            $connect = mysqli_connect("localhost","root","","login-db");
            $query = "SELECT * FROM `battery-status` WHERE 1";
            $result = mysqli_query($connect, $query);
            if ($result->num_rows > 0) {
                // output data of each row
                while($row = $result->fetch_assoc()) {
                    $searchresult = "your battery Level is: ".$row["battery-lvl"]." in: ".$row["time"];
                }
              } else {
                $searchresult = "No Battery Info";
              }
            mysqli_close($connect);
            return $searchresult;
        }
        public function loginUsers($username, $password){
            $json = array();
            $canUserLogin = $this->isLoginExist($username, $password);
            if($canUserLogin){
                $json['success'] = 1;
                $json['message'] = "Successfully logged in";
                $json['result'] = $this->getbatteryinfo();
            }else{
                $json['success'] = 0;
                $json['message'] = "Incorrect details";
            }
            return $json;
        }
    }
?>