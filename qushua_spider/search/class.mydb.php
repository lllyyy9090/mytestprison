<?php 
class MyDb { 
           protected $mysqli; 
           function __construct($host, $user, $pwd, $dbname) {             
              $this->mysqli = @new mysqli($host, $user, $pwd, $dbname); 
              // test whether connection OK 
              if(mysqli_connect_errno()) { 
                 printf("<p>Sorry, no connection! %s\n</p>", 
                  mysqli_connect_error());                
                  $this->mysqli = FALSE; 
                  exit(); 
              } 
           } 
           // destructor (delete object) 
           function __destruct() { 
              $this->close(); 
           } 
           // explicitly terminate object/connection 
           function close() { 
              if($this->mysqli) 
                $this->mysqli->close(); 
                $this->mysqli = FALSE; 
           } 
           // execute SELECT return object field 
           function queryObjectArray($sql) { 
              if($result = $this->mysqli->query($sql)) { 
                if($result->num_rows) { 
                  while($row = $result->fetch_object()) 
                    $result_array[] = $row; 
                  return $result_array; } 
                else 
                  return FALSE; 
              } else { 
                printf("<p>Error: %s</p>\n", $this->mysqli->error); 
                return FALSE; 
              } 
           } 
           // execute SELECT return individual value 
           // Note: return value for error is -1 (not 0)! 
           function querySingleItem($sql) { 
              if ($result = $this->mysqli->query($sql)) { 
                if ($row=$result->fetch_array()) { 
                  $result->close(); 
                  return $row[0]; 
                } else { 
                  return -1; 
                } 
				} else { 
       printf("<p>Error: %s</p>\n", $this->mysqli->error); 
       return -1; 
     } 
   } 
   // execute SQL command without result (INSERT, DELETE, etc.) 
  function execute($sql) { 
     if ($this->mysqli->real_query($sql)) { 
       return TRUE; 
     } else { 
       print $this->mysqli->error; 
       return FALSE; 
     } 
   } 
   // return insert_id 
  function insertId() { 
     return $this->mysqli->insert_id; 
   } 
} 
	?>