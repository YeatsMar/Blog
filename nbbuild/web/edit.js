function createCategory(id){
    
    var category = prompt("新的分类","");
    if(category===null){
        return;
    }
    
    var xhr=new XMLHttpRequest();
    xhr.open("post","Edit",false);
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.send("id="+encodeURIComponent(id)+"&create=true&category="+encodeURIComponent(category));
    
    var status=xhr.getResponseHeader("CreateStatus");
    if(!(status===null)){
        alert("创建失败！");
    } else {
        
        document.getElementById("category").innerHTML +="<option selected=\"selected\" value=\""+category+"\">"+category+"</option>";
        alert("创建成功！");
        
    }
}

function validate(){
    if(document.getElementById("title").value==="") {
        alert("请填写标题");
        return false;
    }
    
    if(document.getElementById("passage").value==="") {
        alert("请填写正文");
        return false;
    }
    
    if(document.getElementById("category").value==="default_option"){
        alert("请选择分类");
        return false;
    }
    
    if(document.getElementById("tags").value==="") {
        alert("请填写标签");
        return false;
    }
    
    var tags=document.getElementById("tags").value.replace(',',' ').split(' '),i,j;
    for(i=0;i<tags.length;i++) {
        for(j=i+1;j<tags.length;j++) {
            if(tags[i]===tags[j]){
                alert("标签有重复");
                return false;
            }
        }
    }
    
    return true;
}