export interface TileVIEW{
    tileId: number;
    tileTitle: string;
    tileData: string;
    ownerUserId: number;
    createdAt: Date;
    updatedAt: Date;
    groupId: number;
    tags: Map<string, string>;
    likesCount: number;
    tileLikedByTheUser: boolean;
    ownerUserName: string;
    groupName: string;
    groupImageLink: string;
    tagsNotEmpty: boolean;
    commentsCount: number;
    userSavedTile: boolean;
}