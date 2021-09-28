import axios from "axios";
import React, { useContext, useEffect } from "react";
import {useImmer} from 'use-immer';
import { NavLink, useParams, Switch, Route } from "react-router-dom";
import Page from "./Page";
import ProfilePosts from "./ProfilePosts";
import StateContext from "../StateContext";
import ProfileFollowers from "./ProfileFollowers";
import ProfileFollowings from "./ProfileFollowings";

function Profile() {
  
  const { profileId } = useParams(); //object de-structuring

  const appState = useContext(StateContext);
  
  const[state, setState] = useImmer({
    disableButton : false,
    startFollowingRequestCount : 0,
    stopFollowingRequestCount : 0,
    profileData :  {
      profileName : "...",
      profileAvatar : "https://gravatar.com/avatar/b9408a09298632b5151200f3449434ef?s=128",
      isFollowing : false,
      counts :  
            {
              postCount : "",
              followerCount : "",
              followingCount : ""
            }
    }
  });

  useEffect(() => {
    const ourRequest = axios.CancelToken.source();
    async function fetchData() {
      try {
        const response = await axios.get(`/profile/${profileId}`, {cancelToken:ourRequest.token});
        console.log( "response  from  server (fetch data for profile)"  + response);
        setState(draft =>  {
          draft.profileData  =  response.data;
        })
      } catch (ex) {
        console.log("An Exception occured during profile data fetch !" + ex);
      }
    }fetchData();//Immediately invoked function
    return ()  => {
      ourRequest.cancel();
    } 
  }, [profileId]);


  function startFollowing(){
    setState(draft =>{
      draft.startFollowingRequestCount++
    });
  }

  useEffect(() => {
    if(state.startFollowingRequestCount){
      setState(draft => {
        draft.disableButton = true;
      })
      const ourRequest = axios.CancelToken.source();
      async function fetchData() {
        try {
          const response = await axios.get(`/follow/${profileId}/addFollowing`, {cancelToken:ourRequest.token});
          console.log( "response from server(addfollwing calll) "  + response);
          if(response.status === 200){
            setState(draft =>  {
              draft.profileData.isFollowing = true;
              draft.profileData.counts.followerCount++;
              draft.disableButton = false;
            });
          }
          
        } catch (ex) {
          console.log("An Exception occured during profile data fetch !" + ex);
        }
      }fetchData();//Immediately invoked function
      return ()  => {
        ourRequest.cancel();
      }
    }
  }, [state.startFollowingRequestCount]);

  function stopFollowing(){
    setState(draft =>{
      draft.stopFollowingRequestCount++;
    });
  }

  useEffect(() => {
    if(state.stopFollowingRequestCount){
      setState(draft => {
        draft.disableButton = true;
      })
      const ourRequest = axios.CancelToken.source();
      async function fetchData() {
        try {
          const response = await axios.get(`/follow/${profileId}/removeFollowing`, {cancelToken:ourRequest.token});
          console.log( "response from server(addfollwing calll) "  + response);
          if(response.status === 200){
            setState(draft =>  {
              draft.profileData.isFollowing = false;
              draft.profileData.counts.followerCount--;
              draft.disableButton = false;
            });
          }
          
        } catch (ex) {
          console.log("An Exception occured during profile data fetch !" + ex);
        }
      }fetchData();//Immediately invoked function
      return ()  => {
        ourRequest.cancel();
      }
    }
  }, [state.stopFollowingRequestCount]);



  return (
    <Page title="Profile">
      <div>
      <h6><strong>Profile Name ::{"  "} {state.profileData.profileName} </strong></h6>
        <h6><strong>Profile ID ::{"  "} {profileId} </strong></h6>
        <h2>
          <img className="avatar-small"
            src="https://gravatar.com/avatar/b9408a09298632b5151200f3449434ef?s=128"
          />
          {/*condition:1::if user is opening his self profile then he/she not  able to see Following button */}
          {/*condition:2::if user is already follow opened profile then he/she not  able to see Following button */}
         {appState.loggedIn && !state.profileData.isFollowing && appState.user.userId  !==  profileId && state.profileData.profileName !== "..." && (
           <button  onClick={startFollowing} disabled={state.disableButton} 
            className="btn btn-primary btn-sm ml-2">
              {state.profileData.isFollowing} Follow <i className="fas fa-user-plus"></i>
         </button>
         )}  
          
          {appState.loggedIn && state.profileData.isFollowing && appState.user.userId  !==  profileId && state.profileData.profileName !== "..." && (
           <button  onClick={stopFollowing} disabled={state.disableButton} 
            className="btn btn-danger btn-sm ml-2">
              {state.profileData.isFollowing} Stop Following <i className="fas fa-user-times"></i>
         </button>
         )}  
        </h2>

        <div className="profile-nav nav nav-tabs pt-2 mb-4">
          <NavLink exact to={`/profile/${profileId}`} className="nav-item nav-link">
            Posts: {state.profileData.counts.postCount}
          </NavLink>
          <NavLink to={`/profile/${profileId}/followers`} className="nav-item nav-link">
            Followers: {state.profileData.counts.followerCount}
          </NavLink>
          <NavLink to={`/profile/${profileId}/followings`} className="nav-item nav-link">
            Following: {state.profileData.counts.followingCount}
          </NavLink>
        </div>
      </div>
      <Switch>
        <Route exact path="/profile/:profileId">
          <ProfilePosts />
        </Route>
        <Route path="/profile/:profileId/followers">
          <ProfileFollowers/>
        </Route>
        <Route path="/profile/:profileId/followings">
          <ProfileFollowings/>
        </Route>
      </Switch>
    </Page>
  );
}
export default Profile;
