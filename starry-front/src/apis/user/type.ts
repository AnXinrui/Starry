/**
 * user 相关数据的 ts 类型
 */

export interface loginFormData {
    userAccount: string;
    userPassword: string;
}

export interface UserType {
    id: number;
    username: string;
    gender: number;
    userAccount?: string;
    avatarUrl: string;
    profile: string;
    phone?: string;
    email?: string;
    tags?:  string;
    rating: number;
    updatedAt: Date;
    createTime: Date;
}
