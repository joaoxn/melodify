import { Song } from "./song";
import { User } from "./user";

export interface RawPlaylist {
    id: string;
    name: string;
    public: boolean;
    allowedUserIds: string[];
    songIds: string[];
}

export interface Playlist {
    id: string;
    name: string;
    public: boolean;
    allowedUsers: User[];
    songs: Song[];
}
