package grailsgoat

class ProfileController {

    def index() { 
        if (params.id) {
            if (params.id?.isInteger()) {
                // Show the specific user ID
                def userdata = User.get(params.id)

                if (userdata != null) {
                    render(view: "profile", model: [user: userdata])
                    return
                }
            }
        } else {
            def userdata = User.get(session.user.id)

            if (userdata != null) {
                render(view: "profile", model: [user: userdata])
                return
            }
        }

        // The user didn't exist
        redirect(controller: "main", view: "index")
    }

    def resume() {
        if (request.post) {
            // They want to upload a resume
            
            def uploadedfile = request.getFile('file')
            if (!uploadedfile?.empty) {
                uploadedfile.transferTo(new File("web-app/uploads/" + uploadedfile.getOriginalFilename()))
                def user = User.get(session.user.id)

                user.resume = uploadedfile.getOriginalFilename()
                user.save(flush: true)

                response.sendError(200, 'Done')
                render "Uploaded."
                return
            }
            render "Failed."
            return
        } else {
            render(view: "uploadresume")
        }
    }

    def edit() {
    	if (request.post) {
            if (params.firstname && params.lastname && params.description && params.email && params.password && params.passwordconfirm && params.id?.isInteger()) {
                def new_data = params

                def input_password = new_data.password

                new_data.password = new_data.password.encodeAsMD5()

                def user = User.get(params.id)

                if (user && input_password.equals(new_data.passwordconfirm)) {
                    user.properties = new_data
                    user.save(flush: true)

                    session.user = user

                    flash.success = "Your profile has been updated successfully"
                    render(view: "edit", model: [user: user])
                    return
                }
            }

            flash.error = "Your profile could not be updated"
            render(view: "edit", model: [user: session.user])
            return
    	} else {
            if (params.id?.isInteger()) {
                def userdata = User.get(params.id)

                if (userdata != null) {
                   render(view: "edit", model: [user: userdata])
                   return
                }
            }
            redirect(controller: "main", view: "index")
    	}
    }
}
