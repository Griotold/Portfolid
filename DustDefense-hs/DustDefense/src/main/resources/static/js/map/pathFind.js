
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 9 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
//--------------------------------------------------------------------------------------------

$.getJSON("/data/geo/seoulGeo.json", function(geojson){
	
	var data = geojson.features;
	var coordinates = [];
	var name ='';
	
	$.each(data, function(index, val){
		coordinates = val.geometry.coordinates;
		name = val.properties.SIG_KOR_NM;
		
		displayArea(coordinates, name);
	})
})

var polygons = [];

function displayArea(coordinates, name){
	var path = [];
	var points = [];
	
	$.each(coordinates[0], function(index, coordinate){
		var point = new Object();
		point.x = coordinate[1];
		point.y = coordinate[0];
		points.push(point);
		path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
		
	})
	
	var polygon = new kakao.maps.Polygon({
		map : map,
		path : path,
		strokeWeight : 2,
		strokeColor : '#004c80',
		strokeOpacity : 1
	});
	
	polygons.push(polygon);
}

//---------------------------------------------------------------------------------------
//----- 실시간 교통 정보 ---------------------------------------------------------------------
var trafficToggle = false;
var trafficSwitch = document.getElementById('trafficInfo');

trafficSwitch.addEventListener('click', function() {
	trafficToggle = !trafficToggle;
	
	switch (trafficToggle){
		case true:
			map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
			break;
		default :
			map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
	}
}, false);
//------------------------------------------------------------------------------------------

//------------------------------------------------------------------------------------------
var startAddress = document.getElementById("startAddress");
var endAddress = document.getElementById("endAddress");

var startCodeVal = [];
var endCodeVal = [];


// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
var infowindow = new kakao.maps.InfoWindow({
	zIndex:1,
	removable : true
	});


// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places(); 

var keyword = document.getElementById("keyword");
var searchKeyword = document.getElementById("btnSearchKeyword");

searchKeyword.addEventListener('click', function(){
	if(keyword.value === ''){
		alert("검색어를 입력해주세요.")
	} else{
		// 키워드로 장소를 검색합니다
		ps.keywordSearch(keyword.value, placesSearchCB); 	
	}
	
})


// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB (data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i=0; i<data.length; i++) {
            displayMarker(data[i]);    
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }       

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    } else if(status === kakao.maps.services.Status.ZERO_RESULT){
		alert("검색 결과가 없습니다.");
	} else if(status === kakao.maps.services.Status.ERROR){
		alert("에러 발생");
	}
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
    
    // 마커를 생성하고 지도에 표시합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) 
    });
    
    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>' + 
        						'<button id="selectStart" onclick="getStartCoord(' + place.x + ',' + place.y + ')">' + "출발지" + '</button>' + 
        						'<button id="selectEnd" onclick="getEndCoord(' + place.x + ',' + place.y + ')">' + "목적지" + '</button>');
        infowindow.open(map, marker);  
        
    });

    $(document).on('click','#btnPathFinder', function(){
		infowindow.setMap(null);
   		marker.setMap(null);
   
	});
}

var startAddr = document.getElementById("startAddr");
var endAddr = document.getElementById("endAddr");

function getStartCoord(x, y){
		startCodeVal[0] = x;
		startCodeVal[1] = y;
		console.log(startCodeVal);
		getAddr(y, x, startAddr);
}  

function getEndCoord(x, y){
		endCodeVal[0] = x;
		endCodeVal[1] = y;
		console.log(endCodeVal);
		getAddr(y, x, endAddr);
}  

function getAddr(lat, lng, place){
	let geocoder = new kakao.maps.services.Geocoder();
	
	let coord = new kakao.maps.LatLng(lat, lng);
	let callback = function(result, status){
		if(status === kakao.maps.services.Status.OK){
			console.log(result);
			try{
				place.innerHTML = '<div>'+'--- '+'도로명주소 : ' + result[0].road_address.address_name + '</div>' +
            					  '<div>'+'--- '+'지번 주소 : ' + result[0].address.address_name + '</div>';	
			} catch(e){
				place.innerHTML = '<div>'+'--- '+'지번 주소 : ' + result[0].address.address_name + '</div>';
			}
			
		}
		
	}
	geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
}	
//-------------------------------------------------------------------------------------------


var btnPathFinder = document.getElementById("btnPathFinder");
var pA;
var region = [];


$(document).on('click','#btnPathFinder', function(){
    pA = printRoute(startCodeVal, endCodeVal);
    
});

var distance = document.getElementById("distance");
var duration = document.getElementById("duration");

const printRoute = async (start, destination) => {
   let response = await fetch('https://api.openrouteservice.org/v2/directions/cycling-regular?api_key=5b3ce3597851110001cf624888ae0402478a4e078fb90dfac4b683ab&start=' 
               + start[0] + ',' + start[1] + '&end=' + destination[0] + ',' + destination[1]);
   let data = null;
   let result = null;
   let linePath = null;
   if (response.status === 200){
      data = await response.json();   //기다림
      //console.log(data);
      result = data.features[0];
      //pathResult.setAttribute('value', result);
      
      linePath = result;
      distictRegion(linePath);
      drawPath(linePath);

   } else{
      //pathResult.setAttribute('value', 'error');
      console.log('Error : ' + response.status);
   }
}

var regionArr = new Array();

function distictRegion(path){
	
	
	var pathCoord = path.geometry.coordinates;
	
	console.log(pathCoord);
	
	for(let i = 0; i < pathCoord.length; i++){
		getAddrx(pathCoord[i][1], pathCoord[i][0]);
			
	}
	
}


function getAddrx(lat,lng){
    let geocoder = new kakao.maps.services.Geocoder();

    let coord = new kakao.maps.LatLng(lat, lng);
    let callback = function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            regionArr.push(result[0].address.region_2depth_name);
        }
    }
    geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
}

function sleep(ms) {
  return new Promise((r) => setTimeout(r, ms));
}

var dur1;
function drawPath (path){
	var pathCoord = path.geometry.coordinates;
	
	var paths = [];
	
	var polyline;
	var summary = path.properties.summary;
	var dis = summary["distance"];
	var dur = Math.ceil(summary["duration"]);
	dur1 = dur;
	var setArr;
	var uniqueArr;
	
	var aArr;
	sleep(3000)
		.then(() => aArr = regionArr)
		.then(() => console.log(aArr))
		.then(() => setArr = new Set(aArr))
		.then(() => uniqueArr = [...setArr])
		.then(() => console.log(uniqueArr))
		.then(() => getAveragePollution(uniqueArr));
	

	regionArr = [];
	getTimeStringSeconds(dur);
	
	distance.innerHTML = dis + ' m';
	
	
	for(let j = 0; j < pathCoord.length; j++){
		paths.push(new kakao.maps.LatLng(pathCoord[j][1], pathCoord[j][0]));
	}
	
	// 지도에 표시할 선을 생성합니다
	polyline = new kakao.maps.Polyline({
    	path: paths, // 선을 구성하는 좌표배열 입니다
    	strokeWeight: 5, // 선의 두께 입니다
    	strokeColor: '#f00', // 선의 색깔입니다
    	strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    	strokeStyle: 'solid' // 선의 스타일입니다
	});
	
	// 지도에 선을 표시합니다 
	polyline.setMap(map);  
	
	setCenter(startCodeVal, endCodeVal);
	
	btnRemovePath.addEventListener('click', function(){
		polyline.setMap(null);
		regionArr = [];
		var originCenter = new kakao.maps.LatLng(37.566826, 126.9786567);
		
		map.setCenter(originCenter);
        map.setLevel(9);
	});
}

function getTimeStringSeconds(seconds){
	var hour, min, sec;
	
	hour = parseInt(seconds / 3600);
	min = parseInt((seconds % 3600) / 60);
	sec = seconds % 60;
	
	if(hour.toString().length == 1) hour = "0" + hour;
	if(min.toString().length == 1) min = "0" + min;
	if(sec.toString().length == 1) sec = "0" + sec;
	
	console.log(hour + ":" + min + ":" + sec);
	duration.innerHTML = hour + ":" + min + ":" + sec;
}


var btnRemovePath = document.getElementById('btnDisablePathFinder');



function setCenter(start, destination){
	
	var points = [
		new kakao.maps.LatLng(start[1], start[0]),
		new kakao.maps.LatLng(destination[1], destination[0])
	]
	
	var bounds = new kakao.maps.LatLngBounds();
	var i, marker;
	for(i = 0; i < points.length; i++){
		marker = new kakao.maps.Marker({ position : points[i]});
		marker.setMap(map);
		
		bounds.extend(points[i]);
	}
	
	map.setBounds(bounds);
}

var marker;

function startEndMarkers(start, destination){
	
	
	var startPosition = {
				title : '출발지',
				latlng : new kakao.maps.LatLng(start[0], start[1])
			};
	var endPosition = {
				title : '도착지',
				latlng : new kakao.maps.LatLng(destination[0], destination[1])
			};
	
	
	// 마커 이미지의 이미지 주소입니다
	var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
	
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35); 
    
    // 마커 이미지를 생성합니다    
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
    
    // 마커를 생성합니다
    startMarker = new kakao.maps.Marker({
       	map: map, // 마커를 표시할 지도
       	position: startPosition.latlng, // 마커를 표시할 위치
       	title : startPosition.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
       	image : markerImage // 마커 이미지 
    });
    
    
    endMarker = new kakao.maps.Marker({
       	map: map, // 마커를 표시할 지도
       	position: endPosition.latlng, // 마커를 표시할 위치
       	title : endPosition.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
       	image : markerImage // 마커 이미지 
    });
    startMarker.setMap(map);
    endMarker.setMap(map);

}

//---------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------
var cityCode = {'종로구' : '111123', '중구' : '111121', '용산구' : '111131', '성동구' : '111142', '광진구' : '111141',
			   '동대문구' : '111152', '중랑구' : '111151', '성북구' : '111161', '강북구' : '111291', '도봉구' : '111171', 
			   '노원구' : '111311', '은평구' : '111181', '서대문구' : '111191', '마포구' : '111201', '양천구' : '111301',
			   '강서구' : '111212', '구로구' : '111221', '금천구' : '111281', '영등포구' : '111231', '동작구' : '111241',
			   '관악구' : '111251', '서초구' : '111262', '강남구' : '111261', '송파구' : '111273', '강동구' : '111274'}

//--------------------------------------------------------------------------------------
const getPollutionVal = async (code, type) => {
	const response = await fetch('http://openapi.seoul.go.kr:8088/41796f7462746d7638397263707561/json/ListAirQualityByDistrictService/1/5/' + code);
	if (response.status === 200){
		const data = await response.json();
		return data.ListAirQualityByDistrictService.row[0][type];
	} else{
		throw new Error('Error');
	}
}

var pA;
var pB;


var pArr = []
const pollutionValue = async (code, type) => {
	const pollutionVal = await getPollutionVal(code, type);
	if (type === "PM10"){
		pA = pollutionVal;
		console.log(pA);
		return pA;	
	}

}

var averagePollution;
var inhalePm10Val = document.getElementById("inhalePm10");

async function getAveragePollution(arr){
	var polArr = new Array();
	for (let i = 0; i < arr.length; i++){
		pArr[i] = await pollutionValue(cityCode[arr[i]], 'PM10');
	}
	console.log(pArr);
	
	for (let i = 0; i < pArr.length; i++){
		pArr[i] = parseInt(pArr[i]);
	}
	
	const resultA = pArr.reduce(function add(sum, currValue){
		return sum + currValue;
	}, 0);
	
	const averageA = resultA / pArr.length;
	
	averagePollution = averageA;
	console.log(averageA);
	console.log(dur1);
	
	var oV = (averagePollution * 0.07 * (dur1 / 60)).toFixed(2);
	var percentVA = (((oV / Math.pow(10, 3)) / 45) * 100).toFixed(2);
	
	inhalePm10Val.innerHTML = oV + ' ㎍' +
							'<div>WHO 1일 권장량 45 ㎍/㎥ 의 약 ' + percentVA + '% 를 차지 합니다.</div>';
	
	regionArr = [];
}
//-----------------------------------------------------------------------------------------------------------------------