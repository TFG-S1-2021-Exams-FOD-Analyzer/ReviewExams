class nestedwhile{
    public void while1() {
        int i = 0;
        int y = 0;
        do {
            contador += 1;
        } 
        while (contador<10);
        while(y < 100) {
            i += y + 1;
        }
        y++;
    }
    
    public void while2() {
        int i = 0;
        int y = 0;
        int x = 5;
        while(y < 100) {
            while(x < 500) {
                x += i * y;
            }
            y += 2;
        }
    }
}