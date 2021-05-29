<html>
<body>
USER LOGIN AT
<br>
<br>

   <div id="time">
   </div>

   <div>
   <marquee>made by Raveena</marquee>
   </div>

<br>
<br>

<script type="text/javascript">
function updateTime()
{
document.getElementById("time").innerText=new Date().toString();
}

setInterval(updateTime,1000);
</script>

</body>
</html>