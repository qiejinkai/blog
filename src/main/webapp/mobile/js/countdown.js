 var addTimer = function () {     
        var list = [],     
        interval;     
    
        return function (id, time) {     
            if (!interval)     
                interval = setInterval(go, 1000);     
            list.push({ ele: document.getElementById(id), time: time });     
        }     
    
        function go() {     
            for (var i = 0; i < list.length; i++) {     
                list[i].ele.innerHTML = getTimerString(list[i].time ? list[i].time -= 1 : 0);
                if (!list[i].time)     
                    list.splice(i--, 1);     
            }     
        }     
    
       function numStr(num){
		//if()
		str = '';
		if(num<10){
			str = '<em>0</em><em>'+num+'</em>';
		}else if(num<100){
			num = num.toString();
			str = '<em>'+num.substring(0, 1)+'</em><em>'+num.substring(1, 2)+'</em>';
			
			}else{
			num = num.toString();
			str = '<em>'+num.substring(0, 1)+'</em><em>'+num.substring(1, 2)+'</em><em>'+num.substring(2, 3)+'</em>';
		}
		return str;
		
	}
	function getTimerString(timestamp){
		 var leftsecond = parseInt(timestamp);
		hour = Math.floor(leftsecond/3600);
		var minute=Math.floor((leftsecond-hour*3600)/60); 
		var second=Math.floor(leftsecond-hour*3600-minute*60);
		hourlength = hour.length;
		
		return numStr(hour)+":"+numStr(minute)+":"+numStr(second);
		//$(this).html(numStr(hour)+":"+numStr(minute)+":"+numStr(second));
		
		}
		
} ();   
	
	
	
	
