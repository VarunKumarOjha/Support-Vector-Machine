public class KernelParams {
        /**
         * Type of kernel;
         * = 0 user defined, 
         * = 1 linear,
         * = 2 polynomial,
         * = 3 Gaussian,
         * = 4 tanh.
         */
        public int kernel = 1;
        /** Parameter a and sigma */
        protected double a;
        /** Parameter b */
        protected double b;
        /** Parameter c */
        protected double c;
		
        public KernelParams(int k, double a, double b, double c) {
                this.kernel = k;
                this.a = a;
                this.b = b;
                this.c = c;
        }
        public KernelParams() {
                this(4,1,1,1);
        }
}