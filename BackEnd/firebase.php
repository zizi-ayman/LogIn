<?php

//token = FirebaseInstanceId.getInstance().getToken();
//$token=$_POST['token'];
//$title=$_POST['title'];
//$body=$_POST['body'];
function sendnote($msg){
    $token='';
    $title='Notification';
    $body= $msg;
    define('API_ACCESS_KEY','');
    $fcmUrl = 'https://fcm.googleapis.com/fcm/send';
    $notification = [
        'title' =>$title,
        'body' => $body,
        'icon' =>'myIcon', 
        'sound' => 'mySound'
    ];
    $extraNotificationData = ["message" => $notification,"moredata" =>'dd'];

    $fcmNotification = [
        //'registration_ids' => $tokenList, //multple token array
        'to'        => $token, //single token
        'notification' => $notification,
        'data' => $extraNotificationData
    ];

    $headers = [
        'Authorization: key=' . API_ACCESS_KEY,
        'Content-Type: application/json'
    ];


    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL,$fcmUrl);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fcmNotification));
    $result = curl_exec($ch);
    curl_close($ch);
}

        //echo $result;
