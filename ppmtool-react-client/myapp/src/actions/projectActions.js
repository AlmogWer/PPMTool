import axios from "axios";
import {DELETE_PROJECT, GET_ERRORS, GET_PROJECT, GET_PROJECTS} from "./types";

export const createProject = (project, history) => async dispatch => {
    try {
        const res = await axios.post("/api/project", project)
        history.push("/dashboard")
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }
}

export const getProjects = () => async dispatch => {
    const res = await axios.get("/api/project/all")
    dispatch({
        type: GET_PROJECTS,
        payload: res.data
    })
}

export const getProject = (id, history) => async dispatch => {
    try {

        const res = await axios.get(`/api/project/${id}`)
        dispatch({
            type: GET_PROJECT,
            payload: res.data
        })
    } catch (e) {
        history.push('/dashboard')
    }

}

export const deleteProject = id => async dispatch => {
    if (window.confirm("Are you sure? This will delete the project and all the data related to it"
    )
    ) {
        await axios.delete(`/api/project/${id}`)
        dispatch({
            type: DELETE_PROJECT,
            payload: id
        })
    }

}