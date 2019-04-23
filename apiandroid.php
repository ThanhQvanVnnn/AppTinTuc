<?php 
	define("DBSERVER", "localhost");
	define("DBUSERNAME","root");
	define("DBPASSWORD","");
	define("DBNAME","apptintuc");

	date_default_timezone_set("Asia/Ho_Chi_Minh");

	$conn = mysqli_connect(DBSERVER,DBUSERNAME,DBPASSWORD,DBNAME);
	$conn->set_charset("utf8");
	if(!$conn){
		die('Connect error : '.mysqli_connect_errno());
	};


	// $ham = $_GET["ham"];

		$ham = $_POST["ham"];

		switch ($ham) {

			case 'LayDanhSachDanhMuc':
				$ham(); 
				break;
			case 'LayDanhSachTinTuc':
				$ham(); 
				break;
			case 'LayDanhSachBinhLuanTheoMa':
				$ham(); 
				break;
			case 'ThemBinhLuan':
				$ham(); 
				break;
			case 'DangNhap':
				$ham(); 
				break;
			case 'DangKy':
				$ham(); 
				break;
			case 'LayUserTheoId':
				$ham(); 
				break;
			case 'LayTinTheoId':
				$ham(); 
				break;
			case 'getTimKiem':
				$ham(); 
				break;			
		}

		//hàm
		function LayDanhSachDanhMuc(){
			global $conn;
			$chuoijson = array();

			if(isset($_POST["ham"])){
				$truyvan = "SELECT * FROM loai_tin";
			$ketqua = mysqli_query($conn,$truyvan);

			if($ketqua){
				while ($dong = mysqli_fetch_array($ketqua)) {
					$chuoijson[] = $dong;
				}
			}

			echo json_encode($chuoijson,JSON_UNESCAPED_UNICODE);
		}
	}

		function LayDanhSachTinTuc(){
			global $conn;
			$chuoijson = array();

			if(isset($_POST["ham"]) || isset($_POST["maLoai"])){
				$maLoai = $_POST["maLoai"];
				$truyvan = "SELECT * FROM tin WHERE tin.id_loaitin ='".$maLoai."'";
			$ketqua = mysqli_query($conn,$truyvan);

			if($ketqua){
				while ($dong = mysqli_fetch_array($ketqua)) {
					$chuoijson[] = $dong;
				}
			}
			echo json_encode($chuoijson,JSON_UNESCAPED_UNICODE);
		}
	}

	function LayDanhSachBinhLuanTheoMa(){
		global $conn;
			$chuoijson = array();

			if(isset($_POST["ham"]) || isset($_POST["maTin"])){
				$maTin = $_POST["maTin"];
				$truyvan = "SELECT * FROM binh_luan WHERE binh_luan.id_tin ='".$maTin."'";
			$ketqua = mysqli_query($conn,$truyvan);

			if($ketqua){
				while ($dong = mysqli_fetch_array($ketqua)) {
					$chuoijson[] = $dong;
				}
			}
			echo json_encode($chuoijson,JSON_UNESCAPED_UNICODE);
		}
	}
	function ThemBinhLuan(){
		global $conn;
		if(isset($_POST["ham"]) || isset($_POST["maTin"]) || isset($_POST["Email"]) || isset($_POST["Date"]) ||
			isset($_POST["idNguoiDung"]) || isset($_POST["noiDung"])){
		$maTin = $_POST["maTin"];
		$Email = $_POST["Email"];
		$Date = $_POST["Date"];
		$idNguoiDung = $_POST["idNguoiDung"];
		$noiDung = $_POST["noiDung"];
		$truyvan = "INSERT INTO binh_luan(email,thoigian,noidung,id_tin,id_user) VALUES ('".$Email."', '".$Date."', '".$noiDung."', '".$maTin."', '".$idNguoiDung."')";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			echo "success";
		}else{
			echo "fail";
		}
		}
	}
	function DangNhap(){
		global $conn;
		$chuoijson = array();

			if(isset($_POST["ham"]) || isset($_POST["email"]) || isset($_POST["password"])){
				$email = $_POST["email"];
				$password = md5($_POST["password"]);
				$truyvan = "SELECT * FROM users WHERE users.email='".$email."' AND users.password='".$password."'";
			$ketqua = mysqli_query($conn,$truyvan);

			if($ketqua){
				while ($dong = mysqli_fetch_array($ketqua)) {
					$chuoijson[] = $dong;
				}
			}
			echo json_encode($chuoijson[0],JSON_UNESCAPED_UNICODE);
		}
	}

	function DangKy(){
		global $conn;
		if(isset($_POST["ham"]) || isset($_POST["email"]) || isset($_POST["password"]) || isset($_POST["username"]) ||
			isset($_POST["sdt"])){
		$email = $_POST["email"];
		$password = md5($_POST["password"]);
		$username = $_POST["username"];
		$sdt = $_POST["sdt"];
		$truyvan = "INSERT INTO users(username,password,email,phone) VALUES ('".$username."', '".$password."', '".$email."', '".$sdt."')";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			echo "success";
		}else{
			echo "fail";
		}
		}
	}
	function LayUserTheoId(){
		global $conn;
		$chuoijson = array();

			if(isset($_POST["ham"]) || isset($_POST["idUser"])){
				$idUser = $_POST["idUser"];
				$truyvan = "SELECT * FROM users WHERE users.id='".$idUser."'";
			$ketqua = mysqli_query($conn,$truyvan);

			if($ketqua){
				while ($dong = mysqli_fetch_array($ketqua)) {
					$chuoijson[] = $dong;
				}
			}
			echo json_encode($chuoijson[0],JSON_UNESCAPED_UNICODE);
		}
	}

	function LayTinTheoId(){
		global $conn;
		$chuoijson = array();

			if(isset($_POST["ham"]) || isset($_POST["id_new"])){
				$id_new = $_POST["id_new"];
				$truyvan = "SELECT * FROM tin WHERE tin.id_tin='".$id_new."'";
			$ketqua = mysqli_query($conn,$truyvan);

			if($ketqua){
				while ($dong = mysqli_fetch_array($ketqua)) {
					$chuoijson[] = $dong;
				}
			}
			echo json_encode($chuoijson[0],JSON_UNESCAPED_UNICODE);
		}
	}

	function getTimKiem(){
		global $conn;
			$chuoijson = array();

			if(isset($_POST["ham"]) || isset($_POST["noidung"])){
				$noidung = $_POST["noidung"];
				$truyvan = "SELECT * FROM tin WHERE tin.tieude LIKE'%".$noidung."%'";
			$ketqua = mysqli_query($conn,$truyvan);

			if($ketqua){
				while ($dong = mysqli_fetch_array($ketqua)) {
					$chuoijson[] = $dong;
				}
			}
			echo json_encode($chuoijson,JSON_UNESCAPED_UNICODE);
		}
	}

 ?>