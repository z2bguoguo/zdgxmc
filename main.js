function opencs() 
{
    a=document.getElementById("open").files;
    c=document.getElementById("git").value;
    d=document.getElementById("out");
    jsons=[];
    for(i=0;i<a.length;i++)
    {
        x=
        {
            "method":"direct",
        };
        x.filename=a[i].name;
        x.download=c+a[i].name;
        jsons.push(x);
    }
    d.innerHTML=JSON.stringify(jsons,null,2);
    console.log(JSON.stringify(jsons,null,2));
}