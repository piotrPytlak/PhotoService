import React, {useCallback, useState} from 'react'
import {Menu} from "@mui/material";
import MenuList from "@mui/material/MenuList";
import MenuItem from "@mui/material/MenuItem";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import {Divider} from "@material-ui/core";
import PhotoAlbumIcon from '@mui/icons-material/PhotoAlbum';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import AddAlbum from "../dialog/AddAlbum";
import AddPhoto from "../dialog/AddPhoto";
import UploadPhotoContext from "../../store/UploadPhotoContext";

const style = {
    width: 320,
    maxWidth: '100%'
}


export default function UploadIconMenu(props) {

    const [albumDialogIsOpen, setAlbumDialogIsOpen] = useState(false)
    const onOpenDialogMenuHandlerAlbum = useCallback(() => setAlbumDialogIsOpen(true), [setAlbumDialogIsOpen])
    const onCloseDialogMenuHandlerAlbum = useCallback(() => setAlbumDialogIsOpen(false), [setAlbumDialogIsOpen])

    const [photoDialogIsOpen, setPhotoDialogIsOpen] = useState(false)
    const onOpenDialogMenuHandlerPhoto = useCallback(() => setPhotoDialogIsOpen(true), [setPhotoDialogIsOpen])
    const onCloseDialogMenuHandlerPhoto = useCallback(() => setPhotoDialogIsOpen(false), [setPhotoDialogIsOpen])

    const {anchor, isOpen, onClose} = props

    return (
        <>

            {photoDialogIsOpen &&
            <UploadPhotoContext>
                <AddPhoto onClose={onCloseDialogMenuHandlerPhoto}/>
            </UploadPhotoContext>
            }
            {albumDialogIsOpen &&
            <AddAlbum onClose={onCloseDialogMenuHandlerAlbum}/>}

            <Menu open={isOpen}
                  sx={style}
                  onClose={onClose}
                  transformOrigin={{
                      vertical: 'top',
                      horizontal: 'center',
                  }}
                  anchorEl={anchor.current}>
                <MenuList>
                    <MenuItem onClick={onOpenDialogMenuHandlerAlbum}>
                        <ListItemIcon>
                            <PhotoAlbumIcon fontSize="small"/>
                        </ListItemIcon>
                        <ListItemText>Add album</ListItemText>

                    </MenuItem>
                    <Divider/>
                    <MenuItem onClick={onOpenDialogMenuHandlerPhoto}>
                        <ListItemIcon>
                            <AddPhotoAlternateIcon fontSize="small"/>
                        </ListItemIcon>
                        <ListItemText>Add photo</ListItemText>
                    </MenuItem>
                </MenuList>
            </Menu>
        </>
    )
}
