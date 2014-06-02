class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"
        {
            controller = "main"
        }
        "/logout"
        {
            controller = "session"
            action = "logout"
        }
        "/profile/edit/$id?"
        {
            controller = "profile"
            action = "edit"
        }
        "/profile/"
        {
            controller = "profile"
            action = "index"
        }
        "/profile/($id)?"
        {
            controller = "profile"
            action = "index"
        }
        "/profile/resume"
        {
            controller = "profile"
            action = "resume"
        }
        "/messages/send/($messageid)?"
        {
            controller = "messages"
            action = "send"
        }
        "/messages/sendto/($userid)?"
        {
            controller = "messages"
            action = "sendto"
        }
        "500"(view:'/error')
	}
}
