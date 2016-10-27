raApp.controller("raDataCtrl",function($scope,$log,$http){
	$scope.hasData = false;
	$scope.hasErrors = false;
	$scope.formData={};
	$scope.logger=function(type,mesg){
		if(type == "INFO"){
			$log.info(mesg);
		}
		if(type == "WARN"){
			$log.warn(mesg);
		}
		if(type == "ERROR"){
			$log.error(mesg);
		}
	};
	$scope.getData=function(){
		var raData="";
		var rankData="";
		var status="";
		var candidate="";
		$scope.hasData = false;
		$scope.hasErrors = false;
		
		//var url = $resource('/rank/candidate/:candidateid/lob/:lobid',{candidateid:$scope.formData.candidateid,lobid:$scope.formData.lobid});
		//$http.get(url)
		$http.get('/rank/candidate/'+$scope.formData.candidateid+'/lob/'+$scope.formData.lobid)
		.then(function success(response){
			raData = response.data;
			//$scope.logger("INFO",raData);
			if(angular.isObject(raData.status)){
				rankData = raData.rankData;
				status = raData.status;
				candidate = raData.candidate;
				$scope.logger("INFO",rankData);
				$scope.logger("INFO",status);
				$scope.logger("INFO",candidate);
				if(angular.isObject(status)){
					
					if(status.code != '200'){
						$scope.status=status;
						$scope.hasErrors = true;
					}else{
						$scope.status=status;
						$scope.candidate=candidate;
						$scope.rankData=rankData;
						$scope.hasData = true;
					}
					
				}
			}
		},function error(response){
			$scope.status=response.data;
		});
		
	};
});