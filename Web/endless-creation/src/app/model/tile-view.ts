export interface TileVIEW{
    tileId: number;
    tileTitle: string;
    tileData: string;
    ownerUserId: number;
    createdAt: Date;
    updatedAt: Date;
    groupId: number;
    tags: Map<String, String>;
}