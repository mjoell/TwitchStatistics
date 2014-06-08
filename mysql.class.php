<?php
class mysql($username, $password) {
	private $db;
	
	function __construct() {
		$this->db = new PDO('mysql:host=' . $host . ';dbname=twitch;charset=utf8', $username, $password);
	}
	
	public function getMostActive($channel, $limit = false) {
		if(!$limit) {
			$stmt = $this->db->prepare("SELECT * FROM ?_count ORDER BY messages");
			$stmt->execute(array($channel));
			$rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
			
			$list = "";
			foreach($rows as $row) {
				$username = $row["username"];
				$messages = $row["messages"];
				$list = "$username: $messages messages\n";
			}
		} else {
			$stmt = $this->db->prepare("SELECT * FROM ?_count ORDER BY messages LIMIT ?");
			$stmt->execute(array($channel, $limit));
			$rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
			
			$list = "";
			foreach($rows as $row {
				$username = $row["username"];
				$messages = $row["messages"];
				$list = "$username: $messages messages\n";
			}
	}

?>