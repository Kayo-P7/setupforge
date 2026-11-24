export function urlImagemProduto(imagemUrl: string | null) {
    if (!imagemUrl) return undefined;
    if (/^https?:\/\//i.test(imagemUrl)) return imagemUrl;
    return `https://setupforge-backend.onrender.com/${imagemUrl.replace(/^\//, '')}`;
}
