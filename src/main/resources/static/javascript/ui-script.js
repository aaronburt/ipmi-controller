


async function displayState(DOMe){
    const statusObject = await callStatus();
    document.getElementById(DOMe).innerHTML = `Online: ${statusObject.state}`;
}



async function callStatus(){
    try {
        const fetched = await fetch('/api/power/status', { method: "POST" });
        const result = await fetched.json();
        console.log(result);
        return result;
    } catch(err){
        console.log(err);
    }
}

async function powerOnStatus(state){
    try {
        const fetched = await fetch(`/api/power/${state}`, { method: "POST" });
        const result = await fetched.json();
        console.log(result);
        return result;
    } catch(err){
        console.log(err);
    }
}

async function setFanSpeed(state){
    try {
        const fetched = await fetch(`/api/fanspeed/${state}`, { method: "POST" });
        const result = await fetched.json();
        console.log(result);
    } catch(err){
        console.log(err);
    }
}

