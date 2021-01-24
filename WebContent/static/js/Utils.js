function makeRestConfig(restPath, params, data, method){
	const loggedUser = localStorage.getObject("loggedUser")
	let token = ""
	if(loggedUser){
		token = loggedUser.token;
	}
	return config = {
		method: method,
		url: restPath,
		pageTemp: restPath,
		data: data,
		params : params,
		headers: {
		    Authorization: 'Bearer ' + token
		}
	}
}

function putRestConfig(restPath, params, data){
	return makeRestConfig(restPath, params, data, "put")
}

function deleteRestConfig(restPath, params, data){
	return makeRestConfig(restPath, params, data, "delete")
}

function postRestConfig(restPath, params, data){
	return makeRestConfig(restPath, params, data, "post")
}

function getRestConfig(restPath, params, data){
	return makeRestConfig(restPath, params, data, "get")
}
