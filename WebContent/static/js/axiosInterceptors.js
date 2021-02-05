const UNAUTHORIZED = 401;
const FORBIDEN = 403;

axios.interceptors.response.use(
	response => response,
	error => {
		const {status} = error.response;
		if (status === UNAUTHORIZED || status === FORBIDEN) {
			logoutUser();
	    }

		return Promise.reject(error);
	}
);

function logoutUser() {
	window.localStorage.removeItem("loggedUser");
	window.localStorage.removeItem("shoppingCart");
	store.state.userLoggedIn = false;
	store.state.loggedUsername = null;
	router.replace("/")
}
