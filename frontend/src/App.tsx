// @ts-ignore

import React, {useState} from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import {Divider, List, Menu, Typography} from 'antd';
import axios from "axios";
import {createBrowserRouter, Link, RouterProvider} from "react-router-dom";
import Login from "./layout/Login";
import SearchCelebrityResult from "./layout/SearchCelebrityResult";
import SearchArtworkResult from "./layout/SearchArtworkResult";
import SearchArtwork from "./layout/SearchArtwork";
import AddArtwork from "./layout/AddArtwork";
import { Header } from 'antd/es/layout/layout';
import Register from "./layout/Register";
import EditArtwork from "./layout/EditArtwork";
import ShowArtwork from "./layout/ShowArtwork";
import UserArtwork from "./layout/UserArtwork";

//interface——类似于data class
//类似于c++的struct和class
function App() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <SearchArtwork/>,
        },
        {
            path: "login",
            element: <Login/>,
        },
        {
            path: "register",
            element: <Register/>,
        },
        {
            path: "a/artwork",
            element: <AddArtwork/>,
        },
        {
            path: "e/artwork/:aid",
            element: <EditArtwork/>,
        },
        {
            path: "s/celebrity",
            element: <SearchCelebrityResult/>,
        },
        {
            path: "s/artwork",
            element: <SearchArtworkResult/>,
        },
        {
            path: "show/artwork/:aid",
            element: <ShowArtwork/>,
        },
        {
            path: "s/artwork/user",
            element: <UserArtwork/>
        }
    ]);
    return <div>
        <RouterProvider router={router} />
    </div>
}

export default App
